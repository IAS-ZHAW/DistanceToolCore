rm(values)
setwd("C:/Daten/java-workspace/DistanceToolCore/Daten")
getwd()
values <- read.table(file="isis_compressed.txt", header=FALSE, blank.lines.skip=FALSE, sep=" ", fill=TRUE, flush=FALSE) 

#remove first dataset
values <- values[-c(1),]
#number of entries per dataset
lengths <- values[3]
lengths

#remove first 3 columns
values <- values[,-c(1,2,3)]

indizes <- seq(from=1,to=length(values),by=2)
indizes

#number of maximum columns
maxVal=163
#allocate outputmatrix
bMatrix <- matrix(0, length(values[,1]), maxVal)

values[1,]

length(values[,1])

for(i in 1:length(values[,1])) {
	for(j in 1:lengths[i,1]) {
		bMatrix[i, values[i, 2*(j-1)+1]] = values[i, 2*(j-1)+2]
	}

	#bMatrix[i, unlist(as.list(values[i, indizes][1,]))]=unlist(as.list(values[1, indizes+1][1,]))
	#bMatrix[i, unlist(as.list(values[i, indizes][1,]))]=1
}

write.table (bMatrix, file="isis_recoded.csv", sep=";", quote=F, row.names=F, col.names=F) 