setwd("C:/Daten/java-workspace/DistanceToolCore/Daten/")
bData <- read.csv(file="isis_chemicaldataset.txt",head=FALSE,sep=" ") 
bData <- data.matrix(bData)

#a=c[4], b=c[3], c=c[2], d=c[1]
#Czekanowski
#measurement <- function(c) { (2*c[4])/(2*c[4] + c[2] + c[3]) }
#Ochiai
measurement <- function(c) { c[4]/sqrt((c[4]+c[3])*(c[4]+c[2])) }
roundup <- function(x) { trunc(x+0.5) }

distanceFunction <- function (bData, measurement) {
	result <- matrix(0, length(bData[,1]), length(bData[,1]))
	for (i in 1:length(bData[,1])) {
		v1 <- bData[i,]
		for (j in 1:length(bData[,1])) {
			v2 <- bData[j,]
			summe <- 2*v1 + v2
			input <- c("0"=0,"1"=0,"2"=0,"3"=0)
			anzahl <- table(summe)
			input[names(anzahl)] <- anzahl
			result[i, j] <- measurement(input)
		}
	}
	result
}

result <- distanceFunction(bData, measurement)
#result <- roundup(result)
write.table(result, file="binaryDistance.csv", sep=";", quote=F, row.names=F, col.names=F) 