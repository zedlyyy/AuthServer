console.log('Thank you for using WebStorm 💙')

const clientId = 'client1';


function startOAuth() {
    const redirectURL = 'http://localhost:63343/SS_client_p1/callback.html';
    const state = 'xyz123';
    const scope = 'profile';
    const responseType = 'code';

    const url = `http://localhost:8080/authorize?responseType=${responseType}&redirectURL=${redirectURL}&scope=${scope}&state=${state}&clientID=${clientId}`;
    window.location.href = url;
}
