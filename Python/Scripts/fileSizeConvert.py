SUFFIXES = {1000: ['KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
            1024: ['KiB', 'MiB', 'GiB', 'TiB', 'PiB', 'EiB', 'ZiB', 'YiB']}


def approximate_size(size, a_kilobyte_is_1024_bytes=True):
    '''Convets file size to a readable form.

    Keywords argumests:
    size -- file size in bytes
    a_kilobyte_is_1024_bytes -- if True (default), use of 1024
                                if False, use of 1000

    Returns: string
    '''

    try:
        if size < 0:
            raise ValueError('number must not be negative')
    except TypeError:
        raise TypeError('size must be in bytes')

    multiple = 1024 if a_kilobyte_is_1024_bytes else 1000
    for suffix in SUFFIXES[multiple]:
        size /= multiple
        if size < multiple:
            return '{0:.1f} {1}'.format(size, suffix)

    raise ValueError('number is too large')


if __name__ == '__main__':
    size = 10000000000
    print(approximate_size(size, False))
    print(approximate_size(size))
    # print(approximate_size('wrong value'))
