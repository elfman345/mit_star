#!/usr/bin/env python
import sys

def main():
    f = open("D:/Users/Dameon G Rogers/workspace/MIT_Test_Python/src/test.csv", "r")
    lines = [i for i in f.readlines()]
    lines.sort()
    print("".join(lines))
    array = []
    for i in range(7):
        array.append([])
        for j in range(3):
            array[i].append(i+j)
    rows = str("".join(lines))
    print(rows)
    rows.split("\n")
    columns = rows.split(",")
    print(columns)
    
    for i in range(7):
        for j in range(3):
            array[i][j] = columns
    
    print(array)
    f.close()    

if __name__ == "__main__":
    main()