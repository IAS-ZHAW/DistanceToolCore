setwd("C:/Daten/analysen")
frame <- read.csv(file="wvs-japan.csv",head=TRUE,sep=",") 

colDWf <- c(12:21, 44:45, 50:54, 57, 58, 59, 60:63, 80:89);
colDWf <- c(colDWf, 104, 105:107, 114, 115, 116:121, 148:151, 152:161);
colDWf <- c(colDWf, 162, 185, 187, 192, 198:208, 209, 210:212, 214);

colIWf <- c(22, 23, 24:33, 34:43, 46, 47, 64:67, 122, 124, 125:130);
colIWf <- c(colIWf, 131:147, 163, 164, 170:174, 178, 184, 186);
colIWf <- c(colIWf, 188:191, 193, 221);
 
colPP <- c(28, 231, 234);

cols <- c(colDWf);
#, colIWf, colPP);
stringCols <- c();
for (col in cols) {
	s <- paste("V", col, sep="");
	stringCols <- c(stringCols, s);
}

output=data.matrix(subset(frame, V2==13, stringCols))
write.table (output, file="wvs-japan-red.csv", quote=F, row.names=F, col.names=F)
