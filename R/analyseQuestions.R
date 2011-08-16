setwd("C:/Daten/java-workspace/DistanceToolCore/Daten/wvs")
library(foreign)

frame <- read.spss("wvs2005_v20090901a.sav")

output=data.matrix(frame)
sink("c:/daten/output2.txt", append=FALSE, split=FALSE)

for (i in c(1:5, 7:length(output))) {
  print(i)
  print(table(output[i,]))
}

sink()