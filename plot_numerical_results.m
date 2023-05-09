close all;

data = readmatrix("data.csv");

tick = data(1,:);
gini = data(2,:);
up = data(3,:);
mid = data(4,:);
low = data(5,:);

figure;

subplot(2,1,1);
plot(tick, up, tick, mid, tick, low, "--");
xlabel("Tick");
ylabel("Number of People");
legend("Up","Middle","Low");

subplot(2,1,2);
plot(tick,gini)
xlabel("Time");
ylabel("Gini Index");
axis([0 800 0 1]);
