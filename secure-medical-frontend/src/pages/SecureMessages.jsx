import SecureMessage from "../SecureMessage";

function SecureMessages() {
    return (
        <div className="page-content">

            <div className="page-heading">

                <div>

                    <p className="section-label">
                        DATA PROTECTION
                    </p>

                    <h2>
                        Secure Message Center
                    </h2>

                    <p>
                        Encrypt sensitive medical information
                        and conceal the encrypted payload inside
                        text using steganography.
                    </p>

                </div>

                <span className="secure-badge">
                    🔐 AES + STEGANOGRAPHY
                </span>

            </div>

            <div className="secure-message-info">

                <div className="workflow-step">
                    <span>1</span>
                    <div>
                        <strong>AES Encryption</strong>
                        <small>
                            Medical data is encrypted before concealment.
                        </small>
                    </div>
                </div>

                <div className="workflow-arrow">
                    →
                </div>

                <div className="workflow-step">
                    <span>2</span>
                    <div>
                        <strong>Text Steganography</strong>
                        <small>
                            Encrypted data is hidden inside carrier text.
                        </small>
                    </div>
                </div>

                <div className="workflow-arrow">
                    →
                </div>

                <div className="workflow-step">
                    <span>3</span>
                    <div>
                        <strong>Authorized Extraction</strong>
                        <small>
                            Hidden data is extracted and decrypted.
                        </small>
                    </div>
                </div>

            </div>

            <div className="secure-message-wrapper">

                <SecureMessage />

            </div>

        </div>
    );
}

export default SecureMessages;