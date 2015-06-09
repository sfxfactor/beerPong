import astropy.io.ascii as ascii
#import numpy as np
import matplotlib.pyplot as plt

data = ascii.read("out.dat")
cups = ascii.read("cups-xz.dat")

plt.plot(cups['col1'],cups['col2'],'r')
plt.plot(data['x'],data['z'])
plt.show()
