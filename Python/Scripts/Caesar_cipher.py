ALPHABET = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'V', 'X', 'Y', 'Z']


def checkIfPossible(phrase, shift):
    try:
        phrase = phrase.upper()
    except TypeError:
        raise TypeError('Phrase must be in string format')

    try:
        if not isinstance(shift, int):
            print(f'{shift} is not an int value, changed to {int(shift)}')
            shift = int(shift)
    except TypeError:
        raise TypeError('Shift must be an int value')

    return phrase, shift


def decrypt(phrase, shift):
    '''Decrypts given phrase form Caesar's code to readable text

    Keywords argumets:
    phrase -- phrase in string to decode
    shift -- the offset of alphabet (float is forced to int)

    Returns: string in lower case

    '''

    result = ""

    phrase, shift = checkIfPossible(phrase, shift)

    for letter in phrase:
        if letter in ALPHABET:
            index = ALPHABET.index(letter)
            if index - shift < 0:
                index += len(ALPHABET)
            elif index - shift >= len(ALPHABET):
                index -= len(ALPHABET)

            new_letter = ALPHABET[index - shift]
            result += new_letter

        else:
            result += letter

    return result.lower()


def encrypt(phrase, shift):
    '''Encrypts given phrase with Caesar's code.

    Keywords arguments:
    phrase -- phrase in string to encrypt
    shift -- the offest of alphabet, if it is a float, uses int() to floor it,
                                    trows a ValueError if it is not a number

    Returns: string in lower case

    '''

    phrase, shift = checkIfPossible(phrase, shift)

    result = ""

    for letter in phrase:
        if letter in ALPHABET:
            index = ALPHABET.index(letter)

            if index + shift >= len(ALPHABET):
                index -= len(ALPHABET)
            elif index + shift < 0:
                index += len(ALPHABET)

            new_letter = ALPHABET[index + shift]
            result = result + str(new_letter)

        else:
            result += letter
    return result.lower()


if __name__ == '__main__':
    test_text = 'try me'
    shift = 15

    # print('Inital text was {}'.format(test_text))
    print(f'Initial text was {test_text!r}')

    test_text = encrypt(test_text, shift)
    # print('Encrypted text is now {}'.format(test_text))
    print(f'Encrypted text is now {test_text!r}')

    test_text = decrypt(test_text, shift)
    # print('Decrypted text is {}'.format(test_text))
    print(f'Decrypted text is {test_text!r}')
