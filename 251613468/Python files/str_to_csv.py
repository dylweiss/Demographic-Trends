data = """
State | Population | Total Area | Number of counties | Electoral Votes
Alabama | 5024279 | 52419 | 67 | 9
Alaska | 733391 | 663267 | 27 | 3
Arizona | 7151502 | 113998 | 15 | 11
Arkansas | 3011524 | 53179 | 75 | 6
California | 39538223 | 163696 | 58 | 54
Colorado | 5773714 | 104094 | 63 | 10
Connecticut | 3605944 | 5543 | 8 | 7
Delaware | 989948 | 2489 | 3 | 3
Florida | 21538187 | 65755 | 67 | 30
Georgia | 10711908 | 59425 | 159 | 16
Hawaii | 1455271 | 10931 | 67 | 4
Idaho | 1839106 | 83570 | 44 | 4
Illinois | 12812508 | 57914 | 102 | 19
Indiana | 6785528 | 36418 | 92 | 11
Iowa | 3190369 | 56272 | 99 | 6
Kansas | 2937880 | 82277 | 105 | 6
Kentucky | 4505836 | 40409 | 120 | 8
Louisiana | 4657757 | 51840 | 64 | 8
Maine | 1362359 | 35385 | 16 | 4
Maryland | 6177224 | 12407 | 23 | 10
Massachusetts | 7029917 | 10555 | 14 | 11
Michigan | 10077331 | 96716 | 83 | 15
Minnesota | 5706494 | 86939 | 87 | 10
Mississippi | 2961279 | 48430 | 82 | 6
Missouri | 6154913 | 69704 | 114 | 10
Montana | 1084225 | 147042 | 56 | 4
Nebraska | 1961504 | 77354 | 93 | 5
Nevada | 3104614 | 110561 | 17 | 6
New Hampshire | 1377529 | 9350 | 10 | 4
New Jersey | 9288994 | 8721 | 21 | 14
New Mexico | 2117522 | 121589 | 33 | 5
New York | 20201249 | 54556 | 62 | 28
North Carolina | 10439388 | 53819 | 100 | 16
North Dakota | 779094 | 70700 | 53 | 3
Ohio | 11799448 | 44825 | 88 | 17
Oklahoma | 3959353 | 69898 | 77 | 7
Oregon | 4237256 | 98381 | 36 | 8
Pennsylvania | 13002700 | 46055 | 67 | 19
Rhode Island | 1097379 | 1545 | 5 | 4
South Carolina | 5118425 | 32020 | 46 | 9
South Dakota | 886667 | 77117 | 66 | 3
Tennessee | 6910840 | 42143 | 95 | 11
Texas | 29145505 | 268581 | 254 | 40
Utah | 3271616 | 84899 | 29 | 6
Vermont | 643077 | 9614 | 14 | 3
Virginia | 8631393 | 42774 | 95 | 13
Washington | 7705281 | 71300 | 39 | 12
West Virginia | 1793716 | 24230 | 55 | 4
Wisconsin | 5893718 | 65498 | 72 | 10
Wyoming | 576851 | 97814 | 23 | 3
"""

# Split the data into lines
lines = data.strip().split('\n')

# Split the lines into fields
fields = [line.split('|') for line in lines]

# Strip the whitespace off the fields
for i in range(len(fields)):
    fields[i] = [field.strip() for field in fields[i]]

file = open('states_counties.csv', 'w')

# Write the data
for row in fields:
    file.write(','.join(row) + '\n')

file.close()