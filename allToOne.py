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
		if 'import' in l: importStatements.add(l)
		else: truncated.append(l)
	contents[fileName] = ''.join(truncated)
	
for file in childFiles: readFile(inputPath + "/" + file)

for mainFile in contents:
	if all(keyWord in contents[mainFile] for keyWord in ('main', 'args')):
		print("Main File:", mainFile)
		break


output = open(outputPath, 'w+')
output.write(''.join(importStatements) + "\n")
output.write(''.join(contents[mainFile]) + "\n")

for file in contents:
	if file != mainFile:
		output.write(''.join(contents[file]) + "\n")
