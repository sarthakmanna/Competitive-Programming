import sys
tokens = ''.join(sys.stdin.readlines()).split()[::-1]

def next(): return tokens.pop()
def nextInt(): return int(next())
def nextFloat(): return float(next())
def getIntArray(n): return [nextInt() for _ in range(n)]
def getFloatArray(n): return [nextFloat() for _ in range(n)]
def getStringArray(n): return [next() for _ in range(n)]


testcase = True
def solve(testcase = 1):
    pass


if testcase is None:
    testcaseCount = 1
    while tokens:
        solve(testcaseCount)
        testcaseCount += 1
else:
    testcaseCount = nextInt() if testcase else 1
    for tc in range(testcaseCount):
        solve(tc + 1)
    assert not tokens