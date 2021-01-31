{-# LANGUAGE DataKinds #-}
{-# LANGUAGE TypeOperators #-}

import Data.Matrix
import Data.Modular

main :: IO ()
main = do
  putStrLn $ if (a10 /= 2329) then show a10 ++ " /= 2329" else show a1e12
    where
      a10, a1e12 :: Integer
      a10 = solve 10
      a1e12 = solve $ 10 ^ (12 :: Integer)

solve :: Integer -> Integer
solve n = unMod $ finalState ! (1, 1)
  where finalState = (pow magic (n - 1)) `multStd` startState

startState :: Matrix ModInt
startState = fmap toMod $ fromList 9 1 [1, 0, 0, 0, 0, 0, 1, 0, 0]

magic :: Matrix ModInt
magic = fmap toMod $ fromLists [ [0, 0, 1, 0, 1, 1, 1, 0, 0]
                               , [0, 0, 0, 1, 0, 0, 0, 0, 0]
                               , [1, 0, 0, 0, 0, 0, 0, 1, 0]
                               , [0, 1, 0, 0, 0, 0, 0, 0, 0]
                               , [1, 0, 0, 0, 0, 0, 0, 0, 0]
                               , [1, 0, 0, 0, 0, 0, 0, 0, 1]
                               , [0, 0, 1, 0, 0, 1, 1, 0, 0]
                               , [1, 0, 0, 0, 0, 0, 0, 1, 0]
                               , [1, 0, 0, 0, 0, 0, 0, 0, 1]
                               ]

pow :: Matrix ModInt -> Integer -> Matrix ModInt
pow base power = powImpl base (identity $ nrows base) powerStrict
  where
    powerStrict = if (power >= 0) then power else error "Power can't be negative"
    powImpl _ acc 0 = acc
    powImpl m acc p = if (p `mod` 2 == 1) 
                      then powImpl m (m `multStd` acc) (p - 1)
                      else powImpl (m `multStd` m) acc (p `div` 2)

type ModInt = ℤ/100000000
