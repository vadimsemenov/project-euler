import Data.List (genericLength, (\\))
import Data.Sort (uniqueSort)

main :: IO ()
main = do
  putStrLn $ show solve

maxDigit :: Int
maxDigit = 9

solve :: Integer
solve = sum $ map permutationsCount $ filter partitionPredicate $ choose (genericLength digits) allDigits

permutationsCount :: [Int] -> Integer
permutationsCount partition = oddCount * evenCount
  where
    oddCount  = count (countDifferent partition)  (countSame partition)  (countZeros partition)
    evenCount = count (countDifferent secondPart) (countSame secondPart) 0

    secondPart = allDigits \\ partition
 
    factorial :: Integer -> Integer
    factorial 0 = 1
    factorial n = n * factorial (n - 1)

    countDifferent l = genericLength $ uniqueSort l
    countSame      l = (genericLength l) - (countDifferent l)
    countZeros     l = genericLength $ filter (== 0) l

    count :: Integer -> Integer -> Integer -> Integer
    count differentQty sameQty zerosQty = (total - zerosQty) * (factorial (total - 1)) `div` (2^sameQty)
      where total = differentQty + sameQty

partitionPredicate :: [Int] -> Bool
partitionPredicate list = (totalSum - 2 * listSum) `mod` 11 == 0
  where
    totalSum = sum allDigits
    listSum = sum list

digits, allDigits :: [Int]
digits = [0..maxDigit]
allDigits = digits >>= replicate 2

choose :: Ord a => Integer -> [a] -> [[a]]
choose count list = uniqueSort $ chooseImpl count list id []
  where
    chooseImpl :: Integer -> [a] -> ([a] -> [a]) -> [[a]] -> [[a]]
    chooseImpl 0 _      f acc = (f []) : acc
    chooseImpl _ []     _ acc = acc
    chooseImpl n (x:xs) f acc = chooseImpl (n - 1) xs (f . (x:)) $ chooseImpl n xs f acc

