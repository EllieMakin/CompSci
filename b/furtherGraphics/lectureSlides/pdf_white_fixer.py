#!/usr/bin/env python

import regex as re
import sys
import zlib

def run(filepath):
    with open(filepath, "rb") as f:
        pdf = f.read()
        stream = re.compile(b'.*?FlateDecode.*?stream(.*?)endstream', re.S)

        for s in stream.findall(pdf):
            try:
                text = zlib.decompress(s.strip(b'\n'))
                # Change white to dark blue
                replace = b'\n'+zlib.compress(text.replace(b'1 1 1 rg', b'1 1 1 rg'), 9)+b'\n'
                pdf = pdf.replace(s, replace)
            except zlib.error:
                pass

    with open(filepath[:-4] + "-output.pdf", "wb+") as out:
        out.write(pdf)

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Please specify filepath")
    else:
        run(sys.argv[1])
