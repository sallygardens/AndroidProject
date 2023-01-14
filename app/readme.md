# Installation
The following command installs all necessary packages:

```.bash
cd fairseq
pip install --editable ./
# if you train the text generation model
pip install -r gen_requirements.txt
# if you train the Grammer error correction model
pip install -r gec_requirements.txt
```
The project was tested using Python 3.7.
training process use 4 x 2080ti GPU with cuda version 11.2

