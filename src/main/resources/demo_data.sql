INSERT IGNORE INTO dep VALUES (DEFAULT, 'Отдел управляющих');
INSERT IGNORE INTO dep VALUES (DEFAULT, 'Отдел рабочих');
INSERT IGNORE INTO dep VALUES (DEFAULT, 'Секретный отдел');

INSERT IGNORE INTO empl VALUES (DEFAULT, 1, 'Степан', 'Разин', '1980-12-04', 'razin@mail.ru', 'менеджер по работе', 3000.00);
INSERT IGNORE INTO empl VALUES (DEFAULT, 2, 'Иван', 'Тёркин', '1982-04-10', 'terkin@mail.ru', 'рабочий', 2000.50);
INSERT IGNORE INTO empl VALUES (DEFAULT, 2, 'Анатолий', 'Коронков', '1979-01-05', 'anatoly@gmail.com', 'рабочий', 2300.50);
INSERT IGNORE INTO empl VALUES (DEFAULT, 2, 'Василий', 'Мамаев', '1979-11-15', 'mamaeff@gmail.com', 'рабочий', 2300.50);
INSERT IGNORE INTO empl VALUES (DEFAULT, 2, 'Модест', 'Лобачевский', '1979-11-15', 'lob@gmail.com', 'халтурщик', 1300.99);
INSERT IGNORE INTO empl VALUES (DEFAULT, 3, 'Джеймс', 'Бонд', NULL , 'bond-jamesbond@mi6.com', 'шпион', NULL);
INSERT IGNORE INTO empl VALUES (DEFAULT, 3, 'Максим', 'Исаев', NULL , 'shtirlitz@gb.su', 'разведчик', NULL);