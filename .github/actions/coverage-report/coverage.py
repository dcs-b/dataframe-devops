import sys

filename = sys.argv[1]
f = open(sys.argv[1], "r")

minimum_coverage = int(sys.argv[2])

# Parse coverage report

res = []
res.append({"name": "Instructions", "covered": 0, "total": 0})
res.append({"name": "Branches", "covered": 0, "total": 0})
res.append({"name": "Lines", "covered": 0, "total": 0})
res.append({"name": "Complexity", "covered": 0, "total": 0})
res.append({"name": "Methods", "covered": 0, "total": 0})

f.readline()

for line in f:
    arr = line.split(',')

    for i in range(5):
        a = int(arr[i+3])
        b = int(arr[i+4])
        res[i]["covered"] += b
        res[i]["total"] += a + b

# Print table with parsed report

print("|", end=" ")
for e in res:
    print("|", e["name"], end=" ")
print("|")

print("| ---", end=" ")
for e in res:
    print("| ---", end=" ")
print("|")

print("| Coverage", end=" ")
for e in res:
    e["percentage"] = e["covered"] / e["total"] * 100
    print("|", round(e["percentage"], 2), "%" ,end=" ")
print("|")

print("| Passed", end=" ")
for e in res:
    if e["percentage"] >= minimum_coverage:
        print("|", "✅" ,end=" ")
    else:
        print("|", "❌" ,end=" ")
print("|")
