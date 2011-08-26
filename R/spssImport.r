setwd("C:/Daten/analysen/wvs")
library(foreign)
frame <- read.spss("wvs-foreigner-nmiss1.sav", use.value.labels=F, to.data.frame=T)

#frame$V41
table(frame$V2)
levels(frame$V2)

#attr(frame$V41, "value.labels")

countries <- attr(frame$V2, "value.labels")

for (i in 1:length(countries)) {
	country = countries[i]
	print(country)
	output=data.matrix(subset(frame, V2==country))
	write.table (output, file=paste("country-files/wvs-", names(country), ".csv", sep=""), quote=F, row.names=F, col.names=F)
}