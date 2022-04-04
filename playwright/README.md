# playwright-presentation

1. В папке api находятся тесты api  
2. В папке parallel находятся тесты ui в режиме параллелизации
3. В папке ui находятся тесты ui
4. В папке snapshots находятся визуальные тесты

Для запуска api тестов выполнить в консоли команду
```
npx playwright test --grep @api @mixed
```
Для параллельного запуска нескольких ui тестов внутри одной спеки test1, выполнить в консоли команду
```
npx playwright test test1
```
Для параллельного запуска ui тестов спеки test2, test3 выполнить в консоли команду
```
 npx playwright test test2 test3
```
Для запуска скриншотного тестирования выполнить в консоли команду
```
npx playwright test -g '@snapshot'
```
Для запуска ui без Page Object выполнить в консоли команду
```
npx playwright test --grep @plain
```
Для запуска ui с Page Object выполнить в консоли команду
```
npx playwright test --grep @po
```