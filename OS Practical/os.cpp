#include <iostream>
using namespace std;

int main() {
    int n, tq;

    cout << "Enter number of processes: ";
    cin >> n;

    int bt[n], rt[n];

    cout << "Enter burst time:\n";
    for(int i = 0; i < n; i++) {
        cin >> bt[i];
        rt[i] = bt[i];
    }

    cout << "Enter time quantum: ";
    cin >> tq;

    int time = 0;
    bool done;

    cout << "\nExecution Order:\n";

    do {
        done = true;

        for(int i = 0; i < n; i++) {
            if(rt[i] > 0) {
                done = false;

                if(rt[i] > tq) {
                    time += tq;
                    rt[i] -= tq;
                    cout << "P" << i+1 << " -> ";
                } else {
                    time += rt[i];
                    cout << "P" << i+1 << " -> ";
                    rt[i] = 0;
                }
            }
        }
    } while(!done);

    cout << "End\n";

    return 0;
}