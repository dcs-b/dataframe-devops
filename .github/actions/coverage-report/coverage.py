import sys

input_filename = sys.argv[1]
f = open(input_filename, "r")

minimum_coverage = int(sys.argv[2])

output_filename = sys.argv[3]

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
    length = len(arr)
    j = 0

    for i in range(3, length, 2):
        a = int(arr[i])
        b = int(arr[i+1])
        res[j]["covered"] += b
        res[j]["total"] += a + b
        j += 1

# Print markdown table with parsed report

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
    e["percentage"] = 0 if e["total"] == 0 else round(e["covered"] / e["total"] * 100, 2)
    print("|", e["percentage"], "%" ,end=" ")
print("|")

print("| Passed", end=" ")
for e in res:
    if e["percentage"] >= minimum_coverage:
        print("|", "✅" ,end=" ")
    else:
        print("|", "❌" ,end=" ")
print("|")

# Write JSON file to create coverage badges

f = open(output_filename, "w")
min_percentage = res[0]["percentage"]

for e in res:
    if e["percentage"] < min_percentage:
        min_percentage = e["percentage"]

if min_percentage == 100:
    color = "brightgreen"
elif 90 <= min_percentage < 100:
    color = "green"
elif 80 <= min_percentage < 90:
    color = "yellowgreen"
elif 70 <= min_percentage < 80:
    color = "yellow"
elif 60 <= min_percentage < 70:
    color = "orange"
else:
    color = "red"

json_content = "{ \"schemaVersion\": 1, \"label\": \"coverage\", \"message\": \""
json_content += str(min_percentage) + "%\", \"color\": \"" + color + "\" }"
f.write(json_content)
