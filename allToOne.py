import os
import time

while 1:
	try:
		inputPath = r"Input path goes here".replace('\\', '/')
		outputPath = r"Output path goes here".replace('\\', '/')

		inputPaths = list(os.walk(inputPath))

		if not inputPaths:
			raise Exception('inputPath not found')

		childFiles = inputPaths[0][-1]

		print("Merging:")
		print(*childFiles, sep='\n')
		print()

		importStatements = set()
		contents = {}

		mainFile = ''

		def readFile(file_name):
			global mainFile

			file = open(file_name, 'r')
			lines = file.readlines()
			contents[file_name] = ''

			for line in lines:
				if all(keyWord in line for keyWord in ('main', 'args')):
					mainFile = file_name

				if line.startswith('import '):
					importStatements.add(line)
				else:
					contents[file_name] += line

			file.close()

		for file in childFiles:
			readFile(inputPath + "/" + file)

		print('Main File:', mainFile)
		print()

		output = open(outputPath, 'w')

		if importStatements:
			output.write(''.join(importStatements) + "\n")

		output.write(''.join(contents[mainFile]) + "\n")

		for file in contents:
			if file != mainFile:
				output.write(''.join(contents[file]) + "\n")

		output.close()

		print('Successfully merged the files')

	except Exception as err:
		print('Error:', err)
		print()

	print('-' * 40)
	time.sleep(1)
