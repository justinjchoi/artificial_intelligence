#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Sun Nov  5 14:17:23 2017

@author: Justin Choi (jc8mc) and Skanda Setty (svs4yx)
"""

import numpy as np
y = 1
e = 0.001
maximum_iterations = 1000
MAX = 7
MIN = 0
MAX_CHOICES = 9

def Reward(s):
	if(s[0] != 3 or s[1] != 6):          
		return -1
	else:
		return 0

def valueIteration(U, case):
	my_matrix = np.zeros((7,7))
	iterations = 0
	sigma = 1
	while(sigma >= e or iterations < maximum_iterations):
		iterations += 1
		U = my_matrix
		sigma = 0
		# for each state s in S
		for i in range(MIN, len(U)):
			for j in range(MIN, len(U[0])):
				s = (i, j)
				mylist = [(-1,-1), (-1, 0), (-1, 1), (0, -1), (0, 0), (0, 1), (1, -1), (1, 0), (1,1)]
				maxValue = None
				for a in mylist:
					if case == 1:
						bounding = ( (a[0] + s[0]), (a[1] + s[1]) )
					elif case == 2:
						if(s[1] in range(3, (MAX-1))):
							bounding = ( (a[0] + s[0]-1), (a[1] + s[1]) )
						else:
							bounding = ( (a[0] + s[0]), (a[1] + s[1]) )
					elif case == 3:
						if(s[1] in range(3, (MAX-1))):
							bounding = ( (a[0] + s[0]-2), (a[1] + s[1]) )
						else:
							bounding = ( (a[0] + s[0]), (a[1] + s[1]) )

					if (bounding[0] > (MAX-1)):
						bounding = ((MAX-1), bounding[1])
					if (bounding[0] < MIN):
						bounding = (MIN, bounding[1])
					if (bounding[1] > (MAX-1)):
						bounding = (bounding[0], (MAX-1))
					if (bounding[1] < MIN):
						bounding = (bounding[0], MIN)

					probability = U[bounding[0]][bounding[1]]
					if(maxValue != None):
						maxValue = max(maxValue, probability)
					else:
						maxValue = probability

				if(maxValue != None):
					my_matrix[i][j] = Reward(s) + y*maxValue

				if (abs(my_matrix[i][j] - U[i][j]) > sigma):
					sigma = abs(my_matrix[i][j] - U[i][j])
	return my_matrix

if __name__ == "__main__":
	for i in range(1, 4):
		U = np.zeros((7,7))
		my_matrix = valueIteration(U, i)
		print("case " + str(i))
		for row in my_matrix:
			print(row)
		print("")

