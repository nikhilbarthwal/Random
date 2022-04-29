from PIL import Image

for p in ['1.jpg', '2.jpg', '3.jpg', '4.jpg', '5.jpg', '6.jpg', '7.jpg']:
    im = Image.open(p)
    pix = im.load()
    x = im.size[0]
    y = im.size[1]
    for i in range(x):
        for j in range(y):
            c = pix[i,j]
            r = c[0]
            g = c[1]
            b = c[2]
            z = int((r+b)/2)
            pix[i,j] = (b, g, b)

    im.save('q' + p)

