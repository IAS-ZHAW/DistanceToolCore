function distances=euklid(values)

distances=zeros(length(values));

for i=1:length(values)
    for j = 1:length(values)
        distances(i,j)=sqrt(sum((values(i,:)-values(j,:)).^2));
    end
end

end