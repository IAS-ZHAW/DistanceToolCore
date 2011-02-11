function distances=distance(values)

distances=zeros(length(values));

for i=1:length(values)
    for j = 1:length(values)
        %euklid
        %distances(i,j)=sum(abs(values(i,:)-values(j,:)));
        %canberra
        %distances(i,j)=sum(abs(values(i,:)-values(j,:))./(values(i,:)+values(j,:)));
        %wave-hedges
        distances(i,j)=sum(1-min(values(i,:), values(j,:))./max(values(i,:), values(j,:)));
    end
end

end