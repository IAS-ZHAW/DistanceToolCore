
n=length(cities);
covariances = cov(cities);

[eigenvektorMatrix,eigenwertMatrix] = eig(covariances); 
eigenvektorMatrix
eigenwerte = diag(eigenwertMatrix);
[ew1 index] = max(eigenwerte);
ev1 = eigenvektorMatrix(:,index);
eigenwerte(index) = 0;
[ew2 index] = max(eigenwerte);
ev2 = eigenvektorMatrix(:,index);
eigenwerte(index) = 0;
[ew3 index] = max(eigenwerte);
ev3 = eigenvektorMatrix(:,index);

[ew1 ew2 ew3]
[ev1 ev2 ev3]


means = mean(cities);
noMeans = cities - means(ones(length(cities), 1), :);

coord = [ev1 ev2]'*(noMeans');
%coord = fliplr(coord);
%scatter(coord(1, :), coord(2, :));
%figure()
coord(2, :) =  coord(2, :) .* -1.0;
scatter(coord(1, :), coord(2, :));

%coord = [ev1 ev2 ev3]'*noMeans';
%coord = flipud(coord);
%scatter3(coord(1, :), coord(2, :), coord(3, :));
