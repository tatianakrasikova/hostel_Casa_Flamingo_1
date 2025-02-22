<#-- booking_confirmation.ftlh -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking confirmation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            background-color: #f4f4f4;
            padding: 20px;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #333;
            text-align: center;
        }

        .details {
            background: #fafafa;
            padding: 15px;
            border-radius: 5px;
            margin-top: 10px;
        }

        .booking {
            display: flex;
            align-items: center;
            gap: 15px;
            margin-top: 15px;
            border-bottom: 1px solid #ddd;
            padding-bottom: 10px;
        }

        .booking_img {
            width: 130px;
            height: 130px;
            object-fit: cover;
            border-radius: 5px;
        }

        .booking_info {
            padding-left: 15px;
        }

        .booking_info p {
            margin: 10px 0;
        }

        .footer {
            margin-top: 20px;
            font-size: 12px;
            color: #777;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Hello, ${user.firstName}!</h2>
    <p>Thanks ${user.firstName}! Your booking in our Hostel Casa Flamingo is confirmed. </p>

    <div class="details">
        <#list bookings as booking>
            <div class="booking">
                <img class="booking_img" src="${booking.imageUrl}" alt="Booking image">
                <div class="booking_info">
                    <p><strong>Booking number:</strong> ${booking.bookingNumber}</p>
                    <p><strong>Check-In:</strong> ${booking.entryDate}</p>
                    <p><strong>Check-out:</strong> ${booking.departureDate}</p>
                    <p><strong>Total price:</strong> ${booking.totalPrice} €</p>
                </div>
            </div>
        </#list>

    </div>

    <p>If you have any questions, please do not hesitate to get in touch with us.</p>

    <div class="footer">
        <p>With best regards,</p>
            <p>the team Hostel Casa Flamingo</p>

    </div>
</div>
</body>
</html>
