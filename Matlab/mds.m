

%cities=[0 93 82 133; 93 0 52 60; 82 52 0 111; 133 60 111 0]

n=length(cities);
squared = cities.*cities;
J=eye(n)-1/n*ones(n);
B=-1/2*J*squared*J;
B;

[eigenvektorMatrix,eigenwertMatrix] = eig(B); 
eigenwerte = diag(eigenwertMatrix);
[ew1 index] = max(eigenwerte);
ev1 = eigenvektorMatrix(:,index);
eigenwerte(index) = 0;
[ew2 index] = max(eigenwerte);
ev2 = eigenvektorMatrix(:,index);

[ew1 ew2]
diag([sqrt(ew1) sqrt(ew2)])

coord = [ev1 ev2]*diag([sqrt(ew1) sqrt(ew2)]);
%coord = [ev1 ev2]*diag([sqrt(ew1) sqrt(ew2)]);
%coord = [ev1 ev2]*eye(2);%diag([sqrt(ew1) sqrt(ew2)]);

scatter(coord(:,1), coord(:,2));
