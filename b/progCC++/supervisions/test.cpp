#include <stdio.h>

template<typename T>
class Stack {
public:
    Stack() : head(0) {}
    
    Stack(const Stack& s) {
        Item* ps = s.head;
        Item** ppt = &head;
        while (ps) {
            *ppt = new Item(ps->val);
            ps = ps->next;
            ppt = &(*ppt)->next;
        }
    }
    
    ~Stack() {
        Item* p = head;
        while (p != nullptr) {
            Item* temp = p;
            p = p->next;
            temp->next = nullptr;
            delete temp;
        }
    }
    
    Stack& operator=(const Stack& s) {
        Stack* temp = new Stack(s);
        head = temp->head;
        temp->head = nullptr;
        delete temp;
        return *this;
    }
    
    void push(T v) {
        Item* new_head = new Item(v);
        new_head->next = head;
        head = new_head;
    }
    
    T pop() {
        if (head == nullptr)
            return T();
        T result = head->val;
        Item* temp = head;
        head = head->next;
        temp->next = nullptr;
        delete temp;
        return result;
    }
    
private:
    struct Item {
        T val;
        Item* next;
        Item(T v) : val(v), next(0) {}
    };
    
    Item* head;
};

int main() {
    Stack<char> s;
    s.push('a'), s.push('b'), s.push('c');
    printf("%d\n", s.pop());
    Stack<char> t = Stack<char>(s);
    printf("%d\n", s.pop());
    printf("%d\n", t.pop());
    printf("%d\n", t.pop());
    printf("%d\n", t.pop());
}
