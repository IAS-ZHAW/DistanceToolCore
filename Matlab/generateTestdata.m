N=200;
M=130;
A=zeros(N, M);

for i=1:N
%     for j = 1:M
%         %Multiply
%         %A(i,j)=i*2^j+1; %the '+1' is that not all can be devided by 2. this could increase complexity of the calculations an therefore get better testdata      
%     end
    if i <= 100
        A(i,:)=200*normpdf([1:M],80,15)+10+0.2*randn(1,1);
    else
        A(i,:)=300*normpdf([1:M],50,13)+5+0.3*randn(1,1);
    end
end

A