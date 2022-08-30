import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Group5XMLReader {

    // output path
    public static final String xmlFilePath = "out/group5_result.xml";
    //api to query for the id's
    public static final String PUBMED_API = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/esearch.fcgi?db=pubmed&api_key=4b58e1bdcda404cad3c3879ed5d0816a3d09&term=";
    // the path for the xml file to pull the title of the pubmed articles
    public static File file = new File("4020a1-datasets.xml");
    // arraylist to hold the pubmed article titles
    public static ArrayList<String> pubArticles = new ArrayList<String>();
    //arraylist to hold the ids of the pubmed articles
    public static ArrayList<String> Ids = new ArrayList<String>();
    // the DocumentBuilderFactory to traverse the DOM
    public static final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    /**
     * Our program works to create a DOMs to request information. Firstly, we want to request the article titles from the given XML file.
     * Next, we request the ID's from the pubmed API. We store the results of these requests into their ArrayLists accordingly.
     * This is done in our fillArrayLists method. Then these are printed out in XML format using our writeOutput method.
     *
     * Also, we are using a try and catch to catch an exception and salvage any results obtained up to that point.
     */
    public static void main(String args[]) throws InterruptedException, ParserConfigurationException, SAXException, IOException {

        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        try {
            doc.getDocumentElement().normalize();
            fillArrays( doc, db);
            writeOutput(pubArticles.size());
        } catch (Exception e) {
            e.printStackTrace();
            writeOutput(pubArticles.size() - 1);
        }
    }
    /**
     * @param doc is the Document is the representative of the XML file
     * @param db is the DocumentBuilder is to define the API of the Document and obtain the DOM
     *   The fillArrays method works by initiating a for-loop and calling the getArticleTitle,createConnection and
     *   getID methods. Before calling the getArticleTitle method we first obtain the NodeList of the root node
     *   of the XML file. in this case its "PubmedArticle", we then call the getArticleTitle on all nodes in the
     *   NodeList to get all the titles.
     */
    public static void fillArrays(Document doc, DocumentBuilder db) throws IOException, SAXException, ParserConfigurationException {

        NodeList nodeList = doc.getElementsByTagName("PubmedArticle");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            getArticleTitle(node);
            doc = createConnection(pubArticles.get(i), doc, db);
            getID(doc, db);
        }
    }
    /**
     * @param node is the primary datatype for the DOM of which we traverse
     *      In this method we traverse the nodes in the NodeList to find "ArticleTitle" nodes
     *      we then pull the text from these nodes and fill it into the pubArticles ArrayList
     */
    public static void getArticleTitle(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) node;
            String artTitle = eElement.getElementsByTagName("ArticleTitle").item(0).getTextContent();
            pubArticles.add(artTitle);
        }
    }
    /**
     * @param doc is the Document is the representative of the XML file
     * @param db is the DocumentBuilder is to define the API of the Document and obtain the DOM
     *     In this method we pull the NodeList from the pubmed API's root node which is: "eSearchResult"
     *     We then traverse the NodeList to find "Id" nodes. if the node is not null then we pull the
     *     text and put it in the Ids ArrayList. Otherwise we put null in the Ids ArrayList.
     */
    public static void getID (Document doc, DocumentBuilder db) throws ParserConfigurationException {
        NodeList nodeList2 = doc.getElementsByTagName("eSearchResult");
        for (int itr = 0; itr < nodeList2.getLength(); itr++) {
            db = dbf.newDocumentBuilder();
            Node node2 = nodeList2.item(itr);
            if (node2.getNodeType() == Node.ELEMENT_NODE) {
                if (doc.getElementsByTagName("Id").item(0) != null) {
                    String apiID = doc.getElementsByTagName("Id").item(0).getTextContent();
                    System.out.println(apiID);
                    Ids.add(apiID);
                } else {
                    System.out.println("null");
                    Ids.add("null");
                }
            }
        }
    }
    /**
     * @param articleTitle is the name of the article to be added to the API query
     * @param doc is the Document is the representative of the XML file
     * @param db is the DocumentBuilder is to define the API of the Document and obtain the DOM
     * @return The Document that is representing the server's XML file is returned.
     *                This method first works by encoding the String and encode it in a URL format.
     *                Then a URL connection is formed with the server using the API along with the
     *                string we are querying. Then the document is changed to the server's API
     *                the document is then returned.
     */
    public static Document createConnection(String articleTitle, Document doc, DocumentBuilder db) throws IOException, SAXException {
        String query = java.net.URLEncoder.encode(articleTitle, "UTF-8");
        URLConnection conn = new URL(PUBMED_API + query + "&field=title").openConnection();
        InputStream is = conn.getInputStream();
        doc = db.parse(is);
        return doc;
    }
    /**
     * @param duration is the limit for the for-loop. This usually the size of the arraylist. In this case: pubArticles.size()
     * @return an XML file written out to the filepath of choice
     *          This method is to print out the contents of the ArrayLists into an XML document.
     *          The contents are iterating through each ArrayList and printing it in its proper tag.
     *          The results are then written into a XML file using the FileWriter class.
     */
    public static FileWriter writeOutput(int duration) throws IOException {
        FileWriter results = new FileWriter(xmlFilePath);
        results.write("<PubmedArticleSet>" + "\n");
        for (int k = 0; k < duration; k++) {
            results.write("\t<PubmedArticle>" + "\n");
            results.write("\t\t<PMID>" + Ids.get(k) + "</PMID>" + "\n");
            results.write("\t\t<ArticleTitle>" + pubArticles.get(k) + "</ArticleTitle>" + "\n");
            results.write("\t</PubmedArticle>" + "\n");
        }
        results.write("</PubmedArticleSet>" + "\n");
        System.out.println("Done writing the file");
        results.close();
        return results;
    }
}
