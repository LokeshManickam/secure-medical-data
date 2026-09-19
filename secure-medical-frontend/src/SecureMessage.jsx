import { useState } from "react";
import api from "./api/api";

function SecureMessage() {
    const [medicalData, setMedicalData] = useState("");
    const [carrierText, setCarrierText] = useState("");
    const [stegoText, setStegoText] = useState("");
    const [readText, setReadText] = useState("");
    const [decryptedData, setDecryptedData] = useState("");

    const [message, setMessage] = useState("");
    const [messageType, setMessageType] = useState("");

    const [isCreating, setIsCreating] = useState(false);
    const [isReading, setIsReading] = useState(false);

    const showMessage = (text, type) => {
        setMessage(text);
        setMessageType(type);

        setTimeout(() => {
            setMessage("");
            setMessageType("");
        }, 3500);
    };

    const createSecureMessage = async (event) => {
        event.preventDefault();

        if (!medicalData.trim() || !carrierText.trim()) {
            showMessage(
                "Please enter medical data and carrier text.",
                "error"
            );
            return;
        }

        setIsCreating(true);

        try {
            const response = await api.post(
                "/secure-message/create",
                null,
                {
                    params: {
                        medicalData,
                        carrier: carrierText
                    }
                }
            );

            const generatedStegoText = response.data;

            setStegoText(generatedStegoText);
            setReadText(generatedStegoText);
            setDecryptedData("");

            showMessage(
                "Medical data encrypted and hidden successfully!",
                "success"
            );

        } catch (error) {
            console.error(
                "CREATE SECURE MESSAGE ERROR:",
                error
            );

            showMessage(
                "Failed to create secure message.",
                "error"
            );

        } finally {
            setIsCreating(false);
        }
    };

    const readSecureMessage = async (event) => {
        event.preventDefault();

        if (!readText.trim()) {
            showMessage(
                "Please provide a stego message to extract.",
                "error"
            );
            return;
        }

        setIsReading(true);

        try {
            const response = await api.post(
                "/secure-message/read",
                readText,
                {
                    headers: {
                        "Content-Type": "text/plain"
                    }
                }
            );

            setDecryptedData(response.data);

            showMessage(
                "Medical data extracted and decrypted successfully!",
                "success"
            );

        } catch (error) {
            console.error(
                "READ SECURE MESSAGE ERROR:",
                error
            );

            console.error(
                "Response:",
                error.response?.data
            );

            showMessage(
                "Failed to extract or decrypt the secure message.",
                "error"
            );

        } finally {
            setIsReading(false);
        }
    };

    const copyStegoText = async () => {
        if (!stegoText) {
            showMessage(
                "There is no stego text to copy.",
                "error"
            );
            return;
        }

        try {
            await navigator.clipboard.writeText(stegoText);

            showMessage(
                "Stego text copied to clipboard.",
                "success"
            );

        } catch (error) {
            console.error("COPY ERROR:", error);

            showMessage(
                "Unable to copy stego text.",
                "error"
            );
        }
    };

    const clearAll = () => {
        setMedicalData("");
        setCarrierText("");
        setStegoText("");
        setReadText("");
        setDecryptedData("");
        setMessage("");
        setMessageType("");
    };

    return (
        <div className="secure-message-panel">

            {message && (
                <div className={`page-message ${messageType}`}>
                    <span>
                        {messageType === "success" ? "✓" : "⚠"}
                    </span>

                    {message}
                </div>
            )}

            <div className="secure-message-grid">

                {/* CREATE SECURE MESSAGE */}

                <section className="secure-box">

                    <div className="secure-box-header">

                        <div>
                            <span className="secure-box-icon">
                                🔐
                            </span>

                            <div>
                                <h3>Create Secure Message</h3>

                                <p>
                                    Encrypt medical data and conceal it
                                    inside carrier text.
                                </p>
                            </div>
                        </div>

                    </div>

                    <form
                        className="secure-form"
                        onSubmit={createSecureMessage}
                    >

                        <div className="input-group">
                            <label htmlFor="medicalData">
                                Sensitive Medical Data
                            </label>

                            <textarea
                                id="medicalData"
                                rows="6"
                                placeholder="Enter dummy medical information..."
                                value={medicalData}
                                onChange={(event) =>
                                    setMedicalData(event.target.value)
                                }
                            />

                            <small>
                                AES-GCM encryption is applied before
                                steganographic concealment.
                            </small>
                        </div>

                        <div className="input-group">
                            <label htmlFor="carrierText">
                                Carrier Text
                            </label>

                            <textarea
                                id="carrierText"
                                rows="5"
                                placeholder="Enter ordinary carrier text..."
                                value={carrierText}
                                onChange={(event) =>
                                    setCarrierText(event.target.value)
                                }
                            />

                            <small>
                                The encrypted payload will be hidden
                                inside this text.
                            </small>
                        </div>

                        <button
                            type="submit"
                            className="secure-primary-button"
                            disabled={isCreating}
                        >
                            {isCreating
                                ? "Encrypting & Hiding..."
                                : "🔐 Create Secure Message"}
                        </button>

                    </form>

                    {stegoText && (
                        <div className="stego-result">

                            <div className="result-header">

                                <div>
                                    <strong>
                                        Generated Stego Text
                                    </strong>

                                    <span>
                                        Encrypted payload successfully
                                        concealed.
                                    </span>
                                </div>

                                <button
                                    type="button"
                                    className="copy-button"
                                    onClick={copyStegoText}
                                >
                                    📋 Copy
                                </button>

                            </div>

                            <textarea
                                className="stego-output"
                                value={stegoText}
                                readOnly
                            />

                        </div>
                    )}

                </section>


                {/* EXTRACT SECURE MESSAGE */}

                <section className="secure-box">

                    <div className="secure-box-header">

                        <div>
                            <span className="secure-box-icon">
                                🔓
                            </span>

                            <div>
                                <h3>Extract Secure Message</h3>

                                <p>
                                    Extract the hidden payload and decrypt
                                    the original medical data.
                                </p>
                            </div>
                        </div>

                    </div>

                    <form
                        className="secure-form"
                        onSubmit={readSecureMessage}
                    >

                        <div className="input-group">

                            <label htmlFor="readText">
                                Stego Text
                            </label>

                            <textarea
                                id="readText"
                                rows="11"
                                placeholder="Paste generated stego text here..."
                                value={readText}
                                onChange={(event) =>
                                    setReadText(event.target.value)
                                }
                            />

                            <small>
                                The hidden encrypted payload will be
                                extracted from the text.
                            </small>

                        </div>

                        <button
                            type="submit"
                            className="secure-secondary-button"
                            disabled={isReading}
                        >
                            {isReading
                                ? "Extracting & Decrypting..."
                                : "🔓 Extract & Decrypt"}
                        </button>

                    </form>

                    {decryptedData && (
                        <div className="decrypted-result">

                            <div className="result-header">

                                <div>
                                    <strong>
                                        Decrypted Medical Data
                                    </strong>

                                    <span>
                                        Original data successfully
                                        recovered.
                                    </span>
                                </div>

                                <span className="success-tag">
                                    ✓ Verified
                                </span>

                            </div>

                            <div className="decrypted-output">
                                {decryptedData}
                            </div>

                        </div>
                    )}

                </section>

            </div>

            <div className="secure-footer-actions">

                <div>
                    <strong>🔒 Security workflow complete</strong>

                    <span>
                        AES-GCM encryption + text steganography
                    </span>
                </div>

                <button
                    type="button"
                    className="clear-button"
                    onClick={clearAll}
                >
                    🧹 Clear All
                </button>

            </div>

        </div>
    );
}

export default SecureMessage;