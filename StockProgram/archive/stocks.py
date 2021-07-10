import requests
import json

a = [ {'nikhil':45, 'tarun':78},
      {'nikhil':45, 'tarun':98} ]

b = a[0]
print b['nikhil']

url = 'http://www.google.com/finance/info?q=NASDAQ:GOOG'

response = requests.get(url)
if(response.ok):
    z = response.content
    #data = json.loads(z[0])
    print len(z)

