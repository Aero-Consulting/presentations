const {test, expect} = require('@playwright/test');

const REPO = 'test-repo-1';
const USER = 'magnus80';

// Request context is reused by all tests in the file.
let apiContext;
const API_TOKEN = '';

test.beforeAll(async ({playwright}) => {
    apiContext = await playwright.request.newContext({
        // All requests we send go to this API endpoint.
        baseURL: 'https://api.github.com', extraHTTPHeaders: {
            // We set this header per GitHub guidelines.
            'Accept': 'application/vnd.github.v3+json', // Add authorization token to all requests.
            // Assuming personal access token available in the environment.
            // eslint-disable-next-line no-undef
            'Authorization': `token ${API_TOKEN}`,
        },
    });
});


test.afterAll(async () => {
    // Dispose all responses.
    await apiContext.dispose();
});

test('API: last created issue should be first in the list @api', async ({page}) => {
    const newIssue = await apiContext.post(`/repos/${USER}/${REPO}/issues`, {
        data: {
            title: '[Feature] request 1',
        }
    });
    expect(newIssue.ok()).toBeTruthy();

    await page.goto(`https://github.com/${USER}/${REPO}/issues`);
    const firstIssue = page.locator(`a[data-hovercard-type='issue']`).first();
    await expect(firstIssue).toHaveText('[Feature] request 1');
});
