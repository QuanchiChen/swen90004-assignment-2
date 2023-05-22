% The MATLAB script to plot the numerical results recorded in data.csv
% Author: team 3

close all;

data = readmatrix("data.csv");

tick = data(1,:);
gini = data(2,:);
up = data(3,:);
mid = data(4,:);
low = data(5,:);

figure("visible","off");

plot(tick,low,tick,mid,tick,up);
ax = gca;
ax.ColorOrder = [1 0 0; 0 1 0; 0 0 1];
title("Class Plot")
xlabel("Time");
ylabel("Number of People");
legend("Low","Mid","Up","Location","eastoutside");
saveas(gcf,"Class.png")

plot(tick,gini)
ylim([0 1]);
title("Gini-Index vs. Time")
xlabel("Time");
ylabel("Gini Index");
saveas(gcf,"Gini.png")
