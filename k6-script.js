import http from 'k6/http';
import { check, group, sleep } from 'k6';
import { randomIntBetween } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';

// Configuration
const BASE_URL = 'http://localhost:8080/api';  // Adjust to your API's base URL

// Test configuration
export let options = {
        // Explicitly set to 10 concurrent users
    vus: 10,         // Number of virtual users
    duration: '2m',  // Total test duration
/*     stages: [
        { duration: '30s', target: 50 },    // Ramp up to 10 users
        { duration: '1m', target: 50 },     // Stay at 10 users for 1 minute
        { duration: '1m', target: 30 },     // Stay at 10 users for 1 minute
        { duration: '30s', target: 0 }      // Ramp down to 0 users
    ], */
    thresholds: {
        http_req_duration: ['p(95)<500'],   // 95% of requests must complete under 500ms
        http_req_failed: ['rate<0.01']      // Error rate should be less than 1%
    }
};

// Utility function to generate random customer data
function generateCustomer() {
    return {
        name: `TestFirst${randomIntBetween(1, 1000)}`,
        age: randomIntBetween(1, 100)
    };
}

// Utility function to generate random account data
function generateAccount() {
    return {
        name: ['SAVINGS', 'CHECKING'][randomIntBetween(0, 1)],
        balance: randomIntBetween(-10000, 10000)
    };
}

// Customer API Tests
function customerApiTests() {
    group('Customer API Tests', () => {
        let createdCustomerId;

        // Create a new customer
        group('Create Customer', () => {
            let newCustomer = generateCustomer();
            let createResp = http.post(`${BASE_URL}/customers`, JSON.stringify(newCustomer), {
                headers: { 'Content-Type': 'application/json' }
            });

            check(createResp, {
                'create customer status is 200': (r) => r.status === 200,
                'create customer response has id': (r) => {
                    let body = JSON.parse(r.body);
                    createdCustomerId = body.id;
                    return body.id !== undefined;
                }
            });
        });

        // Get all customers
        // group('Get All Customers', () => {
        //     let getAllResp = http.get(`${BASE_URL}/customers`);
        //     check(getAllResp, {
        //         'get all customers status is 200': (r) => r.status === 200,
        //         'get all customers returns list': (r) => {
        //             let body = JSON.parse(r.body);
        //             return Array.isArray(body) && body.length > 0;
        //         }
        //     });
        // });

        // Get customer by ID
        group('Get Customer by ID', () => {
            if (createdCustomerId) {
                let getByIdResp = http.get(`${BASE_URL}/customers/${createdCustomerId}`);
                check(getByIdResp, {
                    'get customer by id status is 200': (r) => r.status === 200,
                    'get customer by id returns correct customer': (r) => {
                        let body = JSON.parse(r.body);
                        return body.id === createdCustomerId;
                    }
                });
            }
        });

        // Update customer
        group('Update Customer', () => {
            if (createdCustomerId) {
                let updateCustomer = generateCustomer();
                let updateResp = http.put(`${BASE_URL}/customers/${createdCustomerId}`,
                    JSON.stringify(updateCustomer),
                    { headers: { 'Content-Type': 'application/json' } }
                );

                check(updateResp, {
                    'update customer status is 200': (r) => r.status === 200
                });
            }
        });
    });
}

// Account API Tests
function accountApiTests() {
    group('Account API Tests', () => {
        let createdCustomerId;
        let createdAccountId;

        // First, create a customer to associate accounts with
        group('Create Customer for Accounts', () => {
            let newCustomer = generateCustomer();
            let createResp = http.post(`${BASE_URL}/customers`, JSON.stringify(newCustomer), {
                headers: { 'Content-Type': 'application/json' }
            });

            check(createResp, {
                'create customer status is 200': (r) => r.status === 200,
                'create customer response has id': (r) => {
                    let body = JSON.parse(r.body);
                    createdCustomerId = body.id;
                    return body.id !== undefined;
                }
            });
        });

        // Create an account for the customer
        group('Create Account', () => {
            if (createdCustomerId) {
                let newAccount = generateAccount();
                let createResp = http.post(`${BASE_URL}/accounts/customer/${createdCustomerId}`,
                    JSON.stringify(newAccount),
                    { headers: { 'Content-Type': 'application/json' } }
                );

                check(createResp, {
                    'create account status is 200': (r) => r.status === 200,
                    'create account response has id': (r) => {
                        let body = JSON.parse(r.body);
                        createdAccountId = body.id;
                        return body.id !== undefined;
                    }
                });
            }
        });

        // Get all accounts
        // group('Get All Accounts', () => {
        //     let getAllResp = http.get(`${BASE_URL}/accounts`);
        //     check(getAllResp, {
        //         'get all accounts status is 200': (r) => r.status === 200,
        //         'get all accounts returns list': (r) => {
        //             let body = JSON.parse(r.body);
        //             return Array.isArray(body) && body.length > 0;
        //         }
        //     });
        // });

        // Get accounts by customer ID
        group('Get Accounts by Customer ID', () => {
            if (createdCustomerId) {
                let getAccountsResp = http.get(`${BASE_URL}/accounts/customer/${createdCustomerId}`);
                check(getAccountsResp, {
                    'get customer accounts status is 200': (r) => r.status === 200,
                    'get customer accounts returns list': (r) => {
                        let body = JSON.parse(r.body);
                        return Array.isArray(body);
                    }
                });
            }
        });

        // Update account balance
        group('Update Account Balance', () => {
            if (createdAccountId) {
                let newBalance = randomIntBetween(500, 5000);
                let updateBalanceResp = http.patch(
                    `${BASE_URL}/accounts/${createdAccountId}/balance`,
                    JSON.stringify(newBalance),
                    { headers: { 'Content-Type': 'application/json' } }
                );

                check(updateBalanceResp, {
                    'update balance status is 200': (r) => r.status === 200
                });
            }
        });
    });
}

// Main test scenario
export default function() {
    // Randomize which tests run to simulate real-world scenarios
    if (randomIntBetween(0, 1) === 0) {
        customerApiTests();
    } else {
        accountApiTests();
    }

}