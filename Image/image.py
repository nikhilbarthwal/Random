from PIL import Image
import sys

filename = sys.argv[1]
input = Image.open(filename)
pix = input.load()
width = input.size[0]
height = input.size[1]
output = Image.new(mode = "RGB", size=(2*width, 2*height))
out = output.load()

for w in range(width):
    for h in range(height):
            c = pix[w, h]
            r = c[0]
            g = c[1]
            b = c[2]
            out[w, h] = (r, g, b)
            out[2*width - 1 - w, h] = (r, g, b)
            out[w, 2*height - 1 - h] = (r, g, b)
            out[2*width - 1 - w, 2*height - 1 - h] = (r, g, b)
output.save('out-' + filename)

