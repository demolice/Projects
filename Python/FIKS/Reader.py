
input = open(
    "C:\\Users\\Daniil\\Desktop\\Projects\\Projects\\Python\\FIKS\\input.txt")
output = open(
    "C:\\Users\\Daniil\\Desktop\\Projects\\Projects\\Python\\FIKS\\outp.txt",
    "w")

iterations = int(input.readline())
now = 0

operations = set(['+', '-'])

for line in input.readlines():
    now += 1

    input_line = line.split()
    a_sum = int(input_line[0])
    numbers = input_line[1:]
    total_sum = 0

    for number in numbers:
        total_sum += int(number)

    if total_sum % 2 == 0 and a_sum % 2 != 0:
        output.write('NELZE\n')
        print(f'{now} nelze 1')
        continue
    elif total_sum % 2 != 0 and a_sum % 2 == 0:
        output.write('NELZE\n')
        print(f'{now} nelze 2')
        continue
    elif a_sum > total_sum:
        output.write('NELZE\n')
        print(f'{now} nelze 3')
        continue

    from Sum import getAllPermutations
    stringToNumber = getAllPermutations(numbers, operations)
    print("size" + str(len(stringToNumber)))
    found = False
    for possible in stringToNumber:
        if stringToNumber[possible] == a_sum:
            output.write('LZE\n')
            found = True
            break
    if not found:
        output.write('NELZE\n')


input.close()
output.close()

print('hotovo')
