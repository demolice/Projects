import itertools


def main():
    produce = 9
    inputNumbers = [5, 5, 1]
    operations = set(['+', '-', '*', '/'])
    stringToNumber = getAllPermutations(inputNumbers, operations)
    for possible in stringToNumber:
        if stringToNumber[possible] == produce:
            print(str(possible))

#
# For each permutation of "inputNumbers", try all of the binary "operations",
# returning a map of formed expressions to their numerical values.
#


def getAllPermutations(inputNumbers, operations):
    toReturn = dict()
    consideredPermutations = set()
    for currentPermutation in itertools.permutations(inputNumbers):
        tupleCurrentPermutation = tuple(currentPermutation)
        if tupleCurrentPermutation not in consideredPermutations:
            consideredPermutations.add(tupleCurrentPermutation)
            toReturn.update(getNumbers(currentPermutation, operations))
    return toReturn

#
# Get a map from an order of operations on the "inputNumbers"
# (where operations are from "operations") to the value they produce.
#


def getNumbers(inputNumbers, operations):
    inputStrings = list()
    newInputNumbers = list()
    for inputNumber in inputNumbers:
        inputStrings.append(str(inputNumber))
        newInputNumbers.append(float(inputNumber))
    return helpGetNumbers(inputStrings,
                          newInputNumbers,
                          operations,
                          dict())

#
# Add to the map "answers" all the possible order of operations on the
# "inputNumbers" (where operations are from "operations").
# Keep track of the current state of the operations in "inputStrings".
#


def helpGetNumbers(inputStrings,
                   inputNumbers,
                   operations,
                   answers):
    if len(inputNumbers) == 1:
        answers[inputStrings[0]] = inputNumbers[0]
    else:
        for firstIndex in range(len(inputNumbers) - 1):
            for operation in operations:
                result = operate(operation,
                                 inputNumbers[firstIndex],
                                 inputNumbers[firstIndex + 1])
                if result is not None:
                    newString = "" + inputStrings[firstIndex] + \
                                operation + \
                        inputStrings[firstIndex + 1] + ""
                    newNumbers, newStrings = replaceNumber(firstIndex,
                                                           result,
                                                           newString,
                                                           inputNumbers,
                                                           inputStrings)
                    helpGetNumbers(newStrings,
                                   newNumbers,
                                   operations,
                                   answers)
    return answers

# Return a new list of "inputNumbers" and "inputStrings" that both have 1
# less element than the inputs. The new lists have the values "newResult"
# and "newString", respectively, at index "index", while removing the
# number at index ("index" + 1).


def replaceNumber(index,
                  newResult,
                  newString,
                  inputNumbers,
                  inputStrings):
    newNumbers = list()
    newStrings = list()
    for currentIndex in range(index):
        newNumbers.append(inputNumbers[currentIndex])
        newStrings.append(inputStrings[currentIndex])
    newNumbers.append(newResult)
    newStrings.append(newString)
    for currentIndex in range(index + 1, len(inputNumbers) - 1):
        newNumbers.append(inputNumbers[currentIndex + 1])
        newStrings.append(inputStrings[currentIndex + 1])
    return newNumbers, newStrings

#
# Given a binary "operation", do the mathematical operation
# on the two arguments.
#


def operate(operation, firstNumber, secondNumber):
    if operation == '+':
        return firstNumber + secondNumber
    if operation == '-':
        return firstNumber - secondNumber
    if operation == '*':
        return firstNumber * secondNumber
    if operation == '/':
        return None if secondNumber == 0 else firstNumber / secondNumber
    raise Exception("Unsupported operation: " + operation)


if __name__ == '__main__':
    main()
