const {test, expect} = require('@playwright/test');

test('example test @snapshot', async ({page}) => {
    await page.goto('https://playwright.dev');
    expect(await page.screenshot()).toMatchSnapshot('landing.png');
    //thresholds
    expect(await page.screenshot()).toMatchSnapshot('home.png', {threshold: 0.2});
    //expect text
    expect(await page.textContent('.hero__title')).toMatchSnapshot('hero.txt');
});
