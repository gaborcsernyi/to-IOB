# to-IOB
Using this code makes it possible to convert corpora into IOB format. The script takes three files: a raw text file, a POS-tagged version of the raw text file, and a file in which the start- and end-positions of the chunks are stored. The output is a 3-column (token, POS-tag, IOB-tag) text file.


General description
-------------------

This script was originally created to convert the Wiki50 corpus [1] 
to IOB format with respect to multiword expressions.

The input of the script is folder in which one text is stored in 3 files:

1. .txt file: raw text file in which the sentences are without annotations;

2. .annotation file: list of multiwords within the raw text file (1) marked by their type and their positions within the file;

3. .pos file: sentences with POS tags.

The corpus is therefore a set of texts stored in different files (see Wiki50 [1] for details). Those files that are related to one single text 
(raw text, multiword types and postions, and the POS tagged text) are to be listed with the same name but with the extensions (specified above) in the same folder.
The script takes all the files in the folder and provides the output in a single file.



Examples
--------

Example for input of the script (1-sentence extracts from Wiki50 [1]):

	.txt file
		Bacteriological water analysis is a method of analysing water to estimate the numbers of bacteria present and, if needed, to find out what sort of bacteria they are. It is a microbiological analytical procedure which uses samples of water and from these samples determines the concentration of bacteria.
	.annotation file:
		[('Bacteriological', 'JJ'), ('water', 'NN'), ('analysis', 'NN'), ('is', 'VBZ'), ('a', 'DT'), ('method', 'NN'), ('of', 'IN'), ('analysing', 'VBG'), ('water', 'NN'), ('to', 'TO'), ('estimate', 'VB'), ('the', 'DT'), ('numbers', 'NNS'), ('of', 'IN'), ('bacteria', 'NNS'), ('present', 'JJ'), ('and', 'CC'), (',', ','), ('if', 'IN'), ('needed', 'VBN'), (',', ','), ('to', 'TO'), ('find', 'VB'), ('out', 'RP'), ('what', 'WP'), ('sort', 'NN'), ('of', 'IN'), ('bacteria', 'NN'), ('they', 'PRP'), ('are', 'VBP'), ('.', '.')] 
	.pos file:
		MWE_COMPOUND_NOUN	0	30
		MWE_VPC	125	133
		MWE_VPC_VERB	125	129
		MWE_VPC_PARTICLE	130	133
		SENT_BOUND	161	164
		...


Example for output of script (set to mark nominal compounds only):

	Bacteriological	JJ	B-NP
	water			NN	I-NP
	analysis		NN	I-NP
	is				VBZ	O
	a				DT	O
	method			NN	O
	of				IN	O
	analysing		VBG	O
	water			NN	O
	to				TO	O
	estimate		VB	O
	the				DT	O
	numbers			NNS	O
	of				IN	O
	bacteria		NNS	O
	present			JJ	O
	and				CC	O
	,				,	O
	if				IN	O
	needed			VBN	O
	,				,	O
	to				TO	O
	find				VB	O
	out				RP	O
	what			WP	O
	sort			NN	O
	of				IN	O
	bacteria		NN	O
	they			PRP	O
	are				VBP	O
	.				.	O

---------------------------------------------------------------------------------------------------------

References
----------

[1] Vincze, V., Nagy T., I., Berend, G. 2011. Multiword expressions and Named Entities in 
the Wiki50 corpus. In Proceedings of RANLP 2011. Hissar, Bulgaria, pp. 289-295. 
