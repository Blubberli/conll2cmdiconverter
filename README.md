# conll2tcfconverter

This repository should contain a java library to convert a conllu file to a tcf file. The library should take a CONLL-U file as input and output a TCF file. In the end, the converter will be integrated into WebLicht as a service.
Have a look at WebLicht: https://weblicht.sfs.uni-tuebingen.de/weblicht/ (you have to login with your student id and password, select Uni Tübingen).
Click "start" and select an example text in german (e.g. "die Fee"). Then click on "advanced".
You can see several services (each service corresponds to a small window). Here we already have a "plaintext2tcf converter". If the text is converted to TCF a lot of annotation services are available. But for now you can click on "UD Pipe Tokenizer", next you can do "UD pipe tagger" and parser. Click on "run tools" and you can download the ConllU file that is produced by WebLicht. Our goal is that at any stage of this pipeline, the user can instead convert the data into TCF so that the user is able to use all the other services that are available for TCF. 

in /examples are a TCF and a CONLL-U file were you can have a look at the structure. The CONLL-U file should look familar, it is a column-based format, were every column corresponds to a different annotation, i.e. the first is the token, the second column the lemma etc.
You can find the detailed specification of the CONLL-U file here:
https://universaldependencies.org/format.html

The columns that can be produced and can be converted into TCF are:
WordForm, Lemma, UPOS, FEATS (this is morphology), (HEAD, DEPREL, DEPS (all needed to create a dependency layer)).
The TCF format is specified here:
https://weblicht.sfs.uni-tuebingen.de/weblichtwiki/index.php/The_TCF_Format

There is a library that helps to create TCF documents with TCF layers (each layer corresponds to a different annotation (e.g. one is for lemmas, one is for tokens)).
There is a tutorial about how to use this library here:
https://weblicht.sfs.uni-tuebingen.de/weblichtwiki/index.php/Reading_and_writing_TCF

We will need a sentence layer, tokens layer, lemma layer, morphology layer, dependency layer, pos tag layer.
If a tagset is needed to be specified you can use "universal-pos" for POS-tags and "universal-dep" for dependencies. 

The library should check which columns are present based on the input file, if a column is not present in CONLL-U it is marked with a "-" symbol. This way you can check which columns are filled in the CONLL file and create an annotation layer only if the same annotation is present in the conll file.

In the end it would be nice to have some tests with different conll files as input (e.g. one only with tokens and POS and one with all columns).

Have fun and ask questions (we can also use comments or the wiki section for questions) ;)
