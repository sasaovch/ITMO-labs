package lab_3.actions;

// Если мне захочется достать яблоко с дерева, то будет своя реализация, идет описание процесса получения
// который тоже может меняться
public interface GetThisObjectAction {
    void getObjectAction (CollectAction collection, DescribeCollectAction decribe);
}
