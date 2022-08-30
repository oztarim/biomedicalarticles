Assignment 1. 

This README.txt assumes that you have the JAVA SE Development kit installed on your machine. If not, please download it here: https://www.oracle.com/java/technologies/downloads/#JDK17

In Windows:

Extract the zip file to your desired directory. The contents should be within a folder named "ITEC4020 - Assignment 1". Inside the folder are 3 files:
	* This README.txt - Contains instructions for running the program in Windows
	* 4020a1-datasets.xml - The dataset provided for this assignment.
	* Group5XMLReader.java - The JAVA program

Once you have extracted the contents and verified that the 3 files are present in the folder within your directory:

1. Right click the Group5XMLReader.java program. Open it using a any text editor (Recommended: Notepad++).
2. Go to line 20. For the String xmlFilePath, you need to modify that string to reflect the directory for your extracted folder. One way is to copy and paste the address location in the address bar in Windows Explorer. Leave the "//group5_result.xml" at the end of the String, as that will be your output file. 
3. Go to line 24. For the File parameter, modify that parameter to reflect the directory for your extracted folder. One way is to copy and paste the address location in the address bar in Windows Explorer. Leave the "//4020a1-datasets.xml" at the end of the String, as that will be your output file.
4. Make sure to save your changes made in the text editor.
5. Hold the Shift key and Right-Click on the extracted folder, look for the option that says "Open PowerShell window here."
6. In the Powershell Window, type in the command "javac Group5XMLReader.java". This is the command to compile the JAVA program. You must compile the program before moving onto step 7. You will know the file has compiled when the Powershell Window prompts you for a new command without any errors. Alternatively, if you look in the extracted folder contents, you will notice that you also have an "Group5XMLReader.CLASS" file.
7. In the Powershell Window, type in the command "java Group5XMLReader.java". This will run the program. You will see output in the PowerShell, these are the Article ID numbers for each of the queries.
8. Once you see the prompt that says "Done writing the file", that means the program has completed it's run.
9. Go into the extracted folder and you will notice that you also have an "group5_result.xml" file. Right-click and open the file in a browser window, and this is the output of the program, with the PMID numbers followed by the title of the article.