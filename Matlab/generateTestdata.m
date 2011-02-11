N=100;
M=11;
A=zeros(N, M);

for i=1:N
    for j = 1:M
        A(i,j)=i*2^j+1; %the '+1' is that not all can be devided by 2. this could increase complexity of the calculations an therefore get better testdata
    end
end

A