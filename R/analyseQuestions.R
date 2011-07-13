setwd("C:/Daten/java-workspace/DistanceToolCore/Daten/wvs")
library(foreign)
"welt"
frame <- read.spss("schweiz.sav", use.value.labels=F, to.data.frame=T)
output=data.matrix(frame)

"hallo"
countries <- levels(frame$V2)
for (country in countries) {
	output=data.matrix(subset(frame, V2==country))
  output[, v < 0]
	write.table (output, file=paste("wvs-", country, ".csv", sep=""), quote=F, row.names=F, col.names=F)
}