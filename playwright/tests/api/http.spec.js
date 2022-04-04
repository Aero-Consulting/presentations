const {test, expect} = require('@playwright/test');
/*
Пробуем как отправляют запросы
*/

test('get with node-fetch', async ({playwright}) => {
    let apiContext = playwright.request.newContext;
    const response = await apiContext.get('https://api.nasa.gov/planetary/apod?api_key=Q5bz7IZm7gPtFsTe4mHdFhYBbEsmk23Ftq5nQJw6');
    expect(response.status).toEqual(200);
});

test('get with supertest', async ({playwright}) => {
    let apiContext = await playwright.request.newContext({
        'Accept': 'application/json',
    });
    const response = await apiContext.get('https://api.nasa.gov/planetary/apod?api_key=Q5bz7IZm7gPtFsTe4mHdFhYBbEsmk23Ftq5nQJw6');
    expect(response.status).toEqual(200);
});

test('post with node-fetch', async ({playwright}) => {
    let apiContext = await playwright.request.newContext({
        'Accept': 'application/json',
    });
    const r = await apiContext.post('apichallenges.herokuapp.com/challenger');
    const token = r.headers['x-challenger'];
    const {status} = await apiContext.get('https://apichallenges.herokuapp.com/challenges', {
        headers: {'X-CHALLENGER': token},
    });
    expect(status).toEqual(200);
});

test('post with supertest', async ({playwright}) => {
    let apiContext = await playwright.request.newContext({
        'Accept': 'application/json',
    });
    const r = await apiContext.post('https://apichallenges.herokuapp.com/challenger');
    expect(r.status).toEqual(201);
});
