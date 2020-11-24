#include<stdio.h>

#define init_employee(X,Y) {(X),(Y),wage_emp}
#define init_manager(X,Y,Z) {(X),(Y),wage_man,(Z)}

typedef struct Employee Em;
struct Employee {
    int hours,salary;
    int (*wage)(Em*);
};

int wage_emp(Em *ths) {
    return ths->hours * ths->salary;
}

typedef struct Manager Mn;
struct Manager {
    int hours,salary;
    int (*wage)(Mn*);
    int bonus;
};

int wage_man(Mn *ths) {
    return ths->hours * ths->salary + ths->bonus;
}

int main(void) {
    Mn m = init_manager(40,10,20);
    Em *e= (Em *) &m;
    printf("%d\n",e->wage(e));
    return 0;
}
