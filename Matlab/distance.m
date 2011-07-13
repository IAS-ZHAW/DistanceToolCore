function distances=distance(values)

distances=zeros(length(values));

for i=1:length(values)
    for j = 1:length(values)
        %euklid
        distances(i,j)=sqrt(sum((values(i,:)-values(j,:)).^2));
        %manhattan
        %distances(i,j)=sum(abs(values(i,:)-values(j,:)));
        %canberra
        %distances(i,j)=sum(abs(values(i,:)-values(j,:))./(values(i,:)+values(j,:)));
        %wave-hedges
        %distances(i,j)=sum(1-min(values(i,:), values(j,:))./max(values(i,:), values(j,:)));
        
        % binary coeffs
        % a = sum(values(i,:) & values(j,:));
        % b = sum(values(i,:)) - a;
        % c = sum(values(j,:)) - a;
        % d = sum(~values(i,:) & ~values(j,:));
        %simple bin
        %distances(i,j)=(a + b)/length(values(i,:));
        %jaccard
        % distances(i,j)=a./(a + b + c);
    end
end

end