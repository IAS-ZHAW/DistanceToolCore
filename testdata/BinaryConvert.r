rm(values)
setwd("C:/Daten/java-workspace/DistanceToolCore/testdata")
getwd()
values <- read.table(file="smallset.isis.txt", header=FALSE, blank.lines.skip=FALSE, sep=" ", fill=TRUE, flush=FALSE) 
#length(values)
#values[,3]
values <- values[,-c(1,2)]
indizes<-seq(from=1,to=length(values),by=2)
indizes

indexCols <- values[,indizes]
maxVal <- max(values[!is.na(indexCols)])
maxVal
bMatrix <- matrix(0, length(values[,1]), maxVal)

for(i in 1:length(values[,1])) {
	bMatrix[i, unlist(as.list(values[i, indizes][1,]))]=1
}

write.table (bMatrix, file="binary.csv", sep=";", quote=F, row.names=F) 