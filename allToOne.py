while 1:
	try:
	
		import os
		inputPath = "/home/sarthak/IdeaProjects/CompetitiveProgramming/src"
		outputPath = "/home/sarthak/Desktop/codes/Main.java"


		for (parentDir, childDirs, childFiles) in os.walk(inputPath): break
		print ("Merging:", childFiles)

		importStatements = set()
		contents = {}

		def readFile(fileName):
			global importStatements, contents
			file = open(fileName, 'r')
			lines = file.readlines()
			truncated = []
			for l in lines:
				if 'import ' in l: importStatements.add(l)
				else: truncated.append(l)
			contents[fileName] = ''.join(truncated)
			file.close()
			
		for file in childFiles: readFile(inputPath + "/" + file)

		for mainFile in contents:
			if all(keyWord in contents[mainFile] for keyWord in ('main', 'args')):
				print ("Main File:", mainFile)
				break
		for solverFile in contents:
			if all(keyWord in contents[solverFile] for keyWord in ('TESTCASES', 'Solver', 'solve()')):
				print ("Solver File:", solverFile)
				break
		for helperFile in contents:
			if all(keyWord in contents[helperFile] for keyWord in ('InputStream', 'Buffered')):
				print ("Helper File:", helperFile)
				break
		print ("\n")

		if mainFile == solverFile:
			solverFile = None
		if helperFile == mainFile or helperFile == solverFile:
			helperFile = None

		output = open(outputPath, 'w+')
		output.write(''.join(importStatements) + "\n")
		if mainFile != None:
			output.write(''.join(contents[mainFile]) + "\n")
		if solverFile != None:
			output.write(''.join(contents[solverFile]) + "\n")

		for file in contents:
			if file != mainFile and file != solverFile and file != helperFile:
				output.write(''.join(contents[file]) + "\n")

		if helperFile != None:				
			output.write(''.join(contents[helperFile]) + "\n")
		
		output.close()
		
	except:
		print ("Error !!!")
		pass
	
	import time
	time.sleep(.5)
